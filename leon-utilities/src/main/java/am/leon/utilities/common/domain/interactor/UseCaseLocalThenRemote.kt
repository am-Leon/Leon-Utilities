package am.leon.utilities.common.domain.interactor

import am.leon.utilities.common.data.models.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

abstract class UseCaseLocalThenRemote<Domain, in Body> : UseCaseRemote<Domain, Body>() {

    protected abstract fun executeLocalDS(body: Body?): Flow<Domain>

    protected abstract fun performRemoteOperation(domain: Domain?): Boolean

    override operator fun invoke(
        scope: CoroutineScope, body: Body?, multipleInvoke: Boolean,
        onResult: (Resource<Domain>) -> Unit
    ) {
        scope.launch(Dispatchers.Main) {
            if (multipleInvoke.not())
                onResult.invoke(Resource.loading())

            // Run local first
            runFlow(executeLocalDS(body), body, onResult).collect { localData ->
                if (performRemoteOperation(localData)) { // call network and get result
                    runFlow(executeRemoteDS(body), body, onResult).collect {
                        onResult.invoke(invokeSuccessState(it, body))

                        if (multipleInvoke.not())
                            onResult.invoke(Resource.loading(false))
                    }
                } else { // get local
                    onResult.invoke(invokeSuccessState(localData, body))

                    if (multipleInvoke.not())
                        onResult.invoke(Resource.loading(false))
                }
            }
        }
    }

    override operator fun invoke(body: Body?, multipleInvoke: Boolean): Flow<Resource<Domain>> =
        channelFlow {
            if (multipleInvoke.not())
                send(Resource.loading())

            // Run local first
            runFlow(executeLocalDS(body), body) {
                send(it)
            }.collect { localData ->
                if (performRemoteOperation(localData)) { // call network and get result
                    runFlow(executeRemoteDS(body), body) {
                        send(it)
                    }.collect {
                        send(invokeSuccessState(it, body))

                        if (multipleInvoke.not())
                            send(Resource.loading(false))
                    }
                } else { // get local
                    send(invokeSuccessState(localData, body))

                    if (multipleInvoke.not())
                        send(Resource.loading(false))
                }
            }
        }
}
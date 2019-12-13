package eu.k5.soapui.executor

interface Runner {

    fun initContext(context: RunnerContext)

    fun run(context: RunnerContext)

}
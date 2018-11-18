package util

import java.util.concurrent.Executors

import javax.inject.Singleton

import scala.concurrent.ExecutionContext

@Singleton
class FixedSizeExecutionContext extends ExecutionContext {

  val threadPool = Executors.newFixedThreadPool(100)


  override def execute(runnable: Runnable): Unit = {
    threadPool.execute(runnable)
  }

  override def reportFailure(cause: Throwable): Unit = {
    println(cause.toString)
  }
}

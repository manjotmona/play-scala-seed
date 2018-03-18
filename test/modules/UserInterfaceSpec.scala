package modules

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.reflect.ClassTag

import akka.Done
import org.specs2.mutable.Specification
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder

class ModelsTest[T: ClassTag]{
  def fakeApp : Application = {
    new GuiceApplicationBuilder().build()

  }

  lazy val app2do = Application.instanceCache[T]
  lazy val repository : T = app2do(fakeApp)
}
class UserInterfaceSpec extends Specification  {
  val repo = new ModelsTest[UserInterface]

  "user info repositry" should {
    "detail of a user" in {
      val user = repo.repository.UserInfo("manjot","kaur","manjot@gmail.com")
      val storeResult = Await.result(repo.repository.store(user),Duration.Inf)
      storeResult must equalTo(Done)
    }



  }


}

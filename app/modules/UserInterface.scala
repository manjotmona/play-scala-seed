package modules

import javax.inject.Inject

import scala.concurrent.Future

import akka.Done
import play.api.cache.AsyncCacheApi

class UserInterface @Inject()(cache: AsyncCacheApi) {
  case class UserInfo(fname: String,lname: String,email: String)

  def store(userInfo: UserInfo): Future[Done] = {
    cache.set(userInfo.email,userInfo)
  }
  def getUser(email: String): Future[Option[UserInfo]] = {
    cache.get[UserInfo](email)
  }

}

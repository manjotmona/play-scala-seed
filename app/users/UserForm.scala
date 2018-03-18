package users

import play.api.data.Form
import play.api.data.Forms.{mapping,text,email}


case class UserInfo(fname: String,lname: String,email: String)

class UserForm {
  val userForm = Form(mapping(
    "fname" -> text.verifying("",_.nonEmpty),
    "lname" -> text.verifying("",_.nonEmpty),
    "email" -> email
  )(UserInfo.apply)(UserInfo.unapply))

}

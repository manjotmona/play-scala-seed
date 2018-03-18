package controllers

import javax.inject._

import scala.concurrent.Future

import modules.UserInterface
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.I18nSupport
import play.filters.csrf.CSRF
import users.{UserForm, UserInfo}
import scala.concurrent.ExecutionContext.Implicits.global



/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */


case class User(name: String,email: String ,age: Int)
case class UserData(name: String, email: String)


@Singleton
class HomeController @Inject()(userForm: UserForm,userInterface: UserInterface,cc: ControllerComponents) extends AbstractController(cc) with I18nSupport {

 // implicit val messages = cc.messagesApi

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

//  val user1 = User("rahul","r@gmail.com",16)
//  val user2 = User("abd","r@gmail.com",16)
//  val user3 = User("def","r@gmail.com",16)
//  val user4 = User("mnj","r@gmail.com",16)
//
//  val userList = List(user1,user2,user3,user4)
//
//  val userForm = Form(mapping(
//    "name" -> text,
//    "email" -> email
//  )(UserData.apply)(UserData.unapply))
//
//  val userFormConstraints = Form(mapping(
//    "name" -> nonEmptyText,
//    "age" -> nonEmptyText
//  )(UserData.apply)(UserData.unapply))
//
//  //val userData = userForm.bindFromRequest.get



  def index: Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
   // Ok(views.html.index(userForm))
    //Future.successful(Ok(views.html.form(userForm.userForm)))
  throw new Exception
  }

  def storeData: Action[AnyContent] = Action.async { implicit request =>
    userForm.userForm.bindFromRequest().fold(
      formWithError => {
        Future.successful(BadRequest(views.html.form(formWithError)))
      },
      userData => {
        userInterface.getUser(userData.email).flatMap{ optionalRecord =>
          optionalRecord.fold{
            val record = userInterface.UserInfo(userData.fname,userData.lname,userData.email)
            userInterface.store(record).map{_=>
              Ok("Done")
            }
          }{
            _=> Future.successful(InternalServerError("User alredy exists!!"))
          }
        }

      }
    )
  }

//  def getData1(email: String): Action [AnyContent] = Action.async { implicit request =>
//    userInfoRepo.getUser(email).map{optionalRecord =>
//      optionalRecord.fold {
//        NotFound ("Oops! user not found")
//      }{
//        record =>
//          Ok(s"User's FullName is: ${record.fname},${record.lname},${record.email}")
//      }
//    }


    def getData(email: String): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    userInterface.getUser(email).map{optionalRecord =>
      optionalRecord.fold {
      NotFound("user not Found!!")
      }{
        record =>
          Ok(s"User's FullName is: ${record.fname},${record.lname},${record.email}")
      }
    }
    }


 // @CSRF

// def sessionAndFlash(name: String) = Action { implicit request: Request[AnyContent] =>
//   println("hitt")
//   Ok(views.html.user())
// }


//  def createUser = Action { implicit request: Request[AnyContent] =>
//    userForm.bindFromRequest.fold(
//      formWithErrors => {
//        BadRequest
//      },
//      userData => {
//        Redirect(routes.HomeController.sessionAndFlash("mona")).withSession("name" -> userData.name,"email" -> userData.email).flashing("success" -> "Contact saved!")
//       // println("doneeee")
//       // Ok(userData.toString)
//      })
//
//  }

//  def sessionAndFlash = Action { implicit request: Request[AnyContent] =>
//    Ok(views.html.index(userForm))
//  }


//  def saveContact = Action { implicit request =>
//    userForm.bindFromRequest.fold(
//      formWithErrors => {
//        BadRequest(views.html.index(formWithErrors))
//      },
//      contact => {
//        val contactId = Contact.save(contact)
//        Redirect(routes.Application.showContact(contactId)).flashing("success" -> "Contact saved!")
//      }
//    )
//  }








//  def getUser = Action { implicit request: Request[AnyContent] =>
//
//  }






//  def user = Action { implicit request: Request[AnyContent] =>
//    Ok(views.html.index(userForm))
//  }


  //  def home(name:String) = Action { implicit request: Request[AnyContent] =>
//
//    Redirect(routes.HomeController.index(name))
//   }
}

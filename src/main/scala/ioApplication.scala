object ioApplication extends App {
  import domainModel._
  import cats.effect.IO
  import longevity.context.LongevityContext
  import longevity.effect.cats.ioEffect
  import scala.concurrent.ExecutionContext.Implicits.global

  // build the context for our domain model

  val context  = LongevityContext[IO, DomainModel]()
  val repo     = context.repo

  // we are now ready to start persisting users

  val username = Username("sméagol")
  val oldEmail = Email("gollum@gmail.example.com")
  val newEmail = Email("sméagol@gmail.example.com")
  val fullName = context.testDataGenerator.generate[FullName]
  val user     = User(username, oldEmail, fullName)

  // create, retrieve, update, delete

  val io = for {
    created   <- repo.create(user)
    retrieved <- repo.retrieveOne[User](username)
    modified   = retrieved.modify(_.copy(email = newEmail))
    updated   <- repo.update(modified)
    deleted   <- repo.delete(updated)
    _         <- repo.closeConnection
  } yield {
    println(s"created   ${created.get}")
    println(s"retrieved ${retrieved.get}")
    println(s"updated   ${updated.get}")
    println(s"deleted   ${deleted.get}")
  }

  io.unsafeRunSync()

  // shutdown the blocking thread pool for a fast program exit
  longevity.effect.cats.defaultBlockingThreadPool.shutdown()

}

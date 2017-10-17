import longevity.migrations.Migration

package object migrations {

  val username1_to_username2 = { username1: v1.Username => v2.Username(username1.username) }
  val email1_to_email2 = { email1: v1.Email => v2.Email(email1.email) }
  val u1_to_u2 = { u1: v1.User =>
    v2.User(
      username = username1_to_username2(u1.username),
      email = email1_to_email2(u1.email),
      v2.FullName(u1.last, u1.first, u1.title))
  }

  val v1_to_v2 = Migration.builder[v1.DomainModel, v2.DomainModel](None, "v2").update(u1_to_u2).build

}



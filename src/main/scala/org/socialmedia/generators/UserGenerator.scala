package org.socialmedia.generators

import io.alphash.faker._
import org.socialmedia.models.User
import org.socialmedia.generators.DateGenerator.{generateRandomNumber, getBirthday}
import org.socialmedia.generators.CountryUsers.countries

object UserGenerator {

  val personGenerator = Person()
  val internetGenerator = Internet()
  val phoneGenerator = Phone()

  def getSignUpTimeStamp(): Long = System.currentTimeMillis / 1000

  def getCountry(): String = {
    val countriesList = countries.map(_._1).toList
    val randomIndex = generateRandomNumber(0, countriesList.length-1)
    countriesList(randomIndex)
  }

  def apply(id: Int): User = {
   User(
      userId= id,
      username = internetGenerator.username,
      email = internetGenerator.email,
      phone = phoneGenerator.phoneNumber(),
      firstname = personGenerator.firstNameMale,
      lastname = personGenerator.lastName,
      birthday =  getBirthday(generateRandomNumber(15, 50)),
      timestamp = getSignUpTimeStamp(),
      country = getCountry()
    )
  }

}

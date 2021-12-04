package org.socialmedia.generators

import org.joda.time.LocalDate
import java.text.SimpleDateFormat
import java.util.{Date, GregorianCalendar}
import scala.util.Random

object DateGenerator {

  def generateAge(mean: Double, std: Double): Int = {
    val r = new Random
    Math.round(r.nextGaussian * std + mean).toInt
  }

  def generateRandomNumber(start: Int, end: Int): Int = Random.nextInt(end - start + 1) + start

  def generateBirthdayDate(age: Int): Date = {
    val birthdayYear = LocalDate.now().minusYears(age+1).getYear
    val randomBirthdayMonth = generateRandomNumber(1, 12)
    val randomBirthdayDay = generateRandomNumber(1, 28)
    new GregorianCalendar(birthdayYear, randomBirthdayMonth-1, randomBirthdayDay)
      .getTime
  }

  def formatDate(date: Date): String = new SimpleDateFormat("yyyy-MM-dd").format(date)

  def getBirthday(age: Int): String = {
    val date = generateBirthdayDate(age)
    formatDate(date)
  }

  def generateTimeBeforeCreate(maxHours: Double): Int = {
    val minTime = 1
    val maxTime = (maxHours * 3600).toInt
    generateRandomNumber(minTime, maxTime)
  }

}

package org.socialmedia.models

case class User(
                 userId: Int,
                 username: String,
                 email: String,
                 phone: String,
                 firstname: String,
                 lastname: String,
                 birthday: String,
                 timestamp: Long,
                 country: String
               )

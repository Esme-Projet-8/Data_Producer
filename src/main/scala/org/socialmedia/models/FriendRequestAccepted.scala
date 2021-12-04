package org.socialmedia.models

case class FriendRequestAccepted(
                                  accepterId: Int,
                                  requesterId: Int,
                                  timestamp: Long
                                )

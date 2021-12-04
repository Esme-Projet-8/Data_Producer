package org.socialmedia.models

case class FriendRequest(
                          requesterId: Int,
                          receiverId: Int,
                          timestamp: Long
                        )

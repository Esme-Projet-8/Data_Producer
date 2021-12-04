package org.socialmedia.generators

import org.socialmedia.models.FriendRequest

object FriendRequestGenerator {

  def apply(senderId: Int, receiverId: Int): FriendRequest = {
    FriendRequest(
      requesterId = senderId,
      receiverId = receiverId,
      timestamp = System.currentTimeMillis / 1000
    )
  }


}

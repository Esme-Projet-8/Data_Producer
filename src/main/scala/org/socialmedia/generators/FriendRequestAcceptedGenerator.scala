package org.socialmedia.generators

import org.socialmedia.models.FriendRequestAccepted

object FriendRequestAcceptedGenerator {

  def apply(requestedId: Int, accepterId: Int): FriendRequestAccepted = {
    FriendRequestAccepted(
      accepterId = accepterId,
      requesterId = requestedId,
      timestamp = System.currentTimeMillis / 1000
    )
  }

}

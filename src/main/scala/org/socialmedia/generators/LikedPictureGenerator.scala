package org.socialmedia.generators

import org.socialmedia.models.LikedPicture

object LikedPictureGenerator {
  
  def apply(userId: Int, picId: Int): LikedPicture = {
    LikedPicture(
      pictureId = picId,
      userId = userId,
      timestamp = System.currentTimeMillis / 1000
    )
  }

}

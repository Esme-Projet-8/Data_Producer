package org.socialmedia.models

case class LikedVideo(
                       videoId: Int,
                       userId: Int,
                       userTimeSpent: Int,
                       timestamp: Long
                     )

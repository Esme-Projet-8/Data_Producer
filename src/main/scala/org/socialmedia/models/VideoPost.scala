package org.socialmedia.models

case class VideoPost(
                        videoId: Int,
                        publisherId: Int,
                        timestamp: Long,
                        videoDuration: Int
                      )

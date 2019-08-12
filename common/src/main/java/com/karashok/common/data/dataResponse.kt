package com.karashok.common.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name dataResponse
 * @date 2019/08/03 14:24
 **/
data class ArticleItem(var apkLink: String = "",
                       var author: String = "",
                       var chapterId: Int = 0,
                       var chapterName: String = "",
                       var collect: Boolean = false,
                       var courseId: Int = 0,
                       var desc: String = "",
                       var envelopePic: String = "",
                       var fresh: Boolean = false,
                       var id: Int = 0,
                       var link: String = "",
                       var niceDate: String = "",
                       var origin: String = "",
                       var projectLink: String = "",
                       var publishTime: Long = 0,
                       var superChapterId: Int = 0,
                       var superChapterName: String = "",
                       var tags: MutableList<WChatArticleTag> = mutableListOf(),
                       var title: String = "",
                       var type: Int = 0,
                       var userId: Int = 0,
                       var visible: Int = 0,
                       var zan: Int = 0,
                       var originId: Int = 0
)

data class WChatArticleTag(var name: String = "",
                           var url: String = ""
)

data class WxarticleChapters(var courseId: String,
                             var id: String,
                             var name: String,
                             var order: String,
                             var parentChapterId: String,
                             var userControlSetTop: String,
                             var visible: String
)

data class Banner(var desc: String = "",
                  var id: Int = 0,
                  var imagePath: String = "",
                  var isVisible: Int = 0,
                  var order: Int = 0,
                  var title: String = "",
                  var type: Int = 0,
                  var url: String = ""
)

data class HotSearch(var id: String = "",
                     var link: String = "",
                     var name: String = "",
                     var order: String = "",
                     var visible: String = ""
)

data class FriendList(var icon: String = "",
                      var id: String = "",
                      var link: String = "",
                      var name: String = "",
                      var order: String = "",
                      var visible: String = ""
)

data class KnowledgeSystemTab(var children: MutableList<KnowledgeSystemTabChildren> = mutableListOf(),
                              var courseId: String = "",
                              var id: String = "",
                              var name: String = "",
                              var order: String = "",
                              var parentChapterId: String = "",
                              var userControlSetTop: String = "",
                              var visible: String = ""
)

data class KnowledgeSystemTabChildren(var courseId: String = "",
                                      var id: String = "",
                                      var name: String = "",
                                      var order: String = "",
                                      var parentChapterId: String = "",
                                      var userControlSetTop: String = "",
                                      var visible: String = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(courseId)
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(order)
        parcel.writeString(parentChapterId)
        parcel.writeString(userControlSetTop)
        parcel.writeString(visible)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<KnowledgeSystemTabChildren> {
        override fun createFromParcel(parcel: Parcel): KnowledgeSystemTabChildren {
            return KnowledgeSystemTabChildren(parcel)
        }

        override fun newArray(size: Int): Array<KnowledgeSystemTabChildren?> {
            return arrayOfNulls(size)
        }
    }

}
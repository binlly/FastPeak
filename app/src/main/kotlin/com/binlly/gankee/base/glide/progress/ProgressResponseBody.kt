package com.binlly.gankee.base.glide.progress

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*
import java.io.IOException

class ProgressResponseBody(
        private val imageUrl: String,
        private val responseBody: ResponseBody?,
        private val progressListener: OnProgressListener
): ResponseBody() {
    private var bufferedSource: BufferedSource? = null

    override fun contentType(): MediaType? = responseBody?.contentType()

    override fun contentLength(): Long = responseBody?.contentLength() ?: 0

    override fun source(): BufferedSource? {
        responseBody?.let {
            bufferedSource = Okio.buffer(source(responseBody.source()))
        }
        return bufferedSource
    }

    private fun source(source: Source): Source {
        return object: ForwardingSource(source) {
            internal var totalBytesRead: Long = 0

            @Throws(IOException::class) override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                totalBytesRead += if (bytesRead == -1L) 0 else bytesRead

                progressListener.onProgress(imageUrl,
                        totalBytesRead,
                        contentLength(),
                        bytesRead == -1L,
                        null)
                return bytesRead
            }
        }
    }
}

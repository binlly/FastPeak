package com.binlly.gankee.service.account

class AccountServiceImpl private constructor(): AccountService {


    override // return FXAccount.isLogin();
    val isLogin: Boolean
        get() = false

    companion object {
        private var INSTANCE: AccountServiceImpl? = null
        private val userAvatarUrl: String? = null

        fun registerInstance(): AccountServiceImpl {
            return INSTANCE?.let { INSTANCE } ?: AccountServiceImpl()
        }
    }

    // @Override public void logout(FXAccountCallback callback) {
    //     FXAccount.api().logout(callback);
    // }
}

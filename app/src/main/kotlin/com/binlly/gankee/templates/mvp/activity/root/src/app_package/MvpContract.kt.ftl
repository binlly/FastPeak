package ${packageName}


interface ${moduleName}Contract {
    interface View: BaseView {

    }

    interface Presenter: BaseActivityPresenter {
        fun request(observer: RxObserver<${moduleName}Model>)
    }
}

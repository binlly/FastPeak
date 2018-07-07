package ${packageName}


interface ${moduleName}Contract {
    interface View: BaseView {

    }

    interface Presenter: BaseFragmentPresenter {
        fun request(observer: RxObserver<${moduleName}Model>)
    }
}

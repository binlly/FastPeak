package ${packageName}


interface ${moduleName}Contract {
    interface View: SimpleListView<${moduleName}Model.Model> {

    }

    interface Presenter: BaseActivityPresenter {
        fun refresh()

        fun loadMore()
    }
}

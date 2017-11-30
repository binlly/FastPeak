package ${packageName}


interface ${moduleName}Contract {
    interface View: SimpleListView<${moduleName}Model.Model> {

    }

    interface Presenter: BaseFragmentPresenter {
        fun refresh()

        fun loadMore()
    }
}

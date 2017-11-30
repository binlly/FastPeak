package ${packageName}

import ${applicationPackage}.base.net.HttpResult
import ${applicationPackage}.repo.mock.MOCK
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ${apiService}Service {

    @FormUrlEncoded
    @POST(ApiConfig.URL_${(apiService!"demo")?upper_case})
    @MOCK(ApiConfig.URL_${(apiService!"demo")?upper_case})
    fun request(@FieldMap fieldMap: Map<String, String>): Observable<HttpResult<${apiService}Model>>
}

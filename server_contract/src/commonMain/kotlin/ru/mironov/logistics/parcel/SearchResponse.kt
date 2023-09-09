package ru.mironov.logistics.parcel

data class SearchResponse(
    val page: Int = 0,
    val parcels: List<ServerParcel>
) {
    companion object {
        const val SEARCH_QUERY_TAG = "searchBy"
    }
}
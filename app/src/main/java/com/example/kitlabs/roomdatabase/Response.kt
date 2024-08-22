package com.example.kitlabs.roomdatabase

data class Response(
    var `data`: Data?,
    var message: String?,
    var status: String?
) {
    data class Data(
        var all_harvest_data: List<AllHarvestData>?
    ) {
        data class AllHarvestData(
            var Acres: Double?,
            var agronomist: String?,
            var area_supervisor: String?,
            var complete_harvest: Int?,
            var created_at: String?,
            var current_field_name: String?,
            var estimated_loads: String?,
            var estimated_loads_count: String?,
            var farm_name: String?,
            var field_name: String?,
            var field_number: Int?,
            var google_link: String?,
            var harvest_complete: String?,
            var harvest_notes: String?,
            var harvest_order: String?,
            var harvest_started: String?,
            var harvest_type: String?,
            var harvested_loads: String?,
            var harvesters_used: String?,
            var hyperlink: String?,
            var id: Int?,
            var import_id: String?,
            var last_updated_at: String?,
            var last_updated_by: Int?,
            var latitude: String?,
            var legal_description: String?,
            var location: String?,
            var longitude: String?,
            var pattern: String?,
            var perm_field_id: String?,
            var perm_field_name: String?,
            var split_field: String?,
            var start_harvest: Int?,
            var stewarded: String?,
            var updated_at: String?
        )
    }
}
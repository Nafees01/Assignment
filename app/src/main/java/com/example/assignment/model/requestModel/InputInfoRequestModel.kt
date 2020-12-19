package com.example.assignment.model.requestModel

class InputInfoRequestModel {
    var tsync_id = ""
    var name = ""
    var email = ""
    var phone = ""
    var full_address = ""
    var name_of_university = ""
    var graduation_year = ""
    var cgpa = ""
    var experience_in_months = ""
    var current_work_place_name = ""
    var applying_in = ""
    var expected_salary = ""
    var field_buzz_reference = ""
    var on_spot_update_time = ""
    var on_spot_creation_time = ""
    var github_project_url = ""
    var success = true
    var message = "Request processed successfully."
    var cv_file =CvFileModelItem()
}

class CvFileModelItem {
     var id = 0
    var tsync_id = ""

}

<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['UTA_ID']) && isset($_POST['name']) && isset($_POST['email_id']) && isset($_POST['emename']) && isset($_POST['emenum'])) {
	$UTAID = $_POST['UTAID'];
    $Name = $_POST['Name'];
    $EmailID = $_POST['EmailID'];
    $EmeName = $_POST['EmeName'];
    $EmeNum = $_POST['EmeNum'];
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql inserting a new row
    $result = mysql_query("INSERT INTO signup(UTAID, Name, EmailID, EmeName, EmeNum) VALUES('$UTAID', '$Name', '$EmailID', '$EmeName', '$EmeNum')");
 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "User successfully added.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>
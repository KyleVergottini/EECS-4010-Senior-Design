<?php

include_once 'new_connection.php';

class Events {
		
	private $db;
	private $connection;
	
	function __construct() {
		$this -> db = new DB_Connection();
		$this -> connection = $this->db->getConnection();
	}
	
	public function getEvents()
	{
		$query = "Select * from Events";
		$result = mysqli_query($this->connection, $query);
		
		$number_of_rows = mysqli_num_rows($result);
	
		$temp_array  = array();
	
		if($number_of_rows > 0) {
			while ($row = mysqli_fetch_assoc($result)) {
				$temp_array[] = $row;
			}
		}
		
		header('Content-Type: application/json');
		echo json_encode(array("events"=>$temp_array));
		mysqli_close($connect);
		
	}
}

	$events = new Events();
	$events-> getEvents();

	
	
?>
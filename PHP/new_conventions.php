<?php

include_once 'new_connection.php';

class Conventions {
		
	private $db;
	private $connection;
	
	function __construct() {
		$this -> db = new DB_Connection();
		$this -> connection = $this->db->getConnection();
	}
	
	public function getConventions()
	{
		$query = "Select * from Conventions";
		$result = mysqli_query($this->connection, $query);
		
		$number_of_rows = mysqli_num_rows($result);
	
		$temp_array  = array();
	
		if($number_of_rows > 0) {
			while ($row = mysqli_fetch_assoc($result)) {
				$temp_array[] = $row;
			}
		}
		
		header('Content-Type: application/json');
		echo json_encode(array("conventions"=>$temp_array));
		mysqli_close($connect);
		
	}
}

	$conventions = new Conventions();
	$conventions-> getConventions();

	
	
?>
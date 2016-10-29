<?php
include_once 'new_connection.php';
	
	class User {
		
		private $db;
		private $connection;
		
		function __construct() {
			$this -> db = new DB_Connection();
			$this -> connection = $this->db->getConnection();
		}
		
		public function does_user_exist($email,$password)
		{
			$query = "Select * from Users where Username='$email'";
			$result = mysqli_query($this->connection, $query);
			if(mysqli_num_rows($result)>0){
				$json['error'] = 'Username already exists'; //return error flag and duplicate username message
				echo json_encode($json);
				mysqli_close($this -> connection);
			}else{
				$query = "insert into Users (Username, Password) values ('$email','$password')";
				$inserted = mysqli_query($this -> connection, $query);
				if($inserted == 1 ){
					$json['success'] = ''.$email; //return success flag and username
				}else{
					$json['error'] = 'An error has occured'; //return error flag and message
				}
				echo json_encode($json);
				mysqli_close($this->connection);
			}
			
		}
		
	}
	
	$user = new User();
	if(isset($_POST['email'],$_POST['password'])) {
		$email = $_POST['email'];
		$password = $_POST['password'];
		
		if(!empty($email) && !empty($password)){
			
			$encrypted_password = md5($password);
			$user-> does_user_exist($email,$password);
			
		}else{
			$encrypted_password = md5($password);
			echo json_encode("you must type both inputs");
		}
		
	}
?>
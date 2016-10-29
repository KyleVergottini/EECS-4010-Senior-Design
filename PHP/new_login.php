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
			$query = "Select * from Users where Username='$email' and Password = '$password' ";
			$result = mysqli_query($this->connection, $query);
			if(mysqli_num_rows($result)>0){
				$json['success'] = ''.$email;
				echo json_encode($json);
				mysqli_close($this -> connection);
			}else{
				$json['error'] = 'Invalid Username or Password';
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
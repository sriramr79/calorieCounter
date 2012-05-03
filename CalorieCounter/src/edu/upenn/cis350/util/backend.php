<?php

mysql_connect("fling.seas.upenn.edu:3306","zhangka","diablo") or die(mysql_error());

      mysql_select_db("zhangka");

      $q=mysql_query("SELECT * FROM users");

      while($e=mysql_fetch_assoc($q))
		$output[]=$e;
	print(json_encode($output));
//	mysql_query("delete from users");
	
	mysql_close();
?>

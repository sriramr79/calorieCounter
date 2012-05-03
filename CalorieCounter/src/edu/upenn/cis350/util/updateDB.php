<?php

//$command = $_GET["command"];

mysql_connect("fling.seas.upenn.edu:3306","zhangka","diablo") or die(mysql_error());

      mysql_select_db("zhangka");
	//$command = $_POST['command'];
	$command = $_GET['command'];
     $q=mysql_query($command);
	//mysql_query("insert into users values ('zhangka','zhangka','Alex Zhang',90000)");

      //while($e=mysql_fetch_assoc($q))
        //        $output[]=$e;
        //print(json_encode($output));
        //mysql_query("delete from cis350");

        mysql_close();

?>

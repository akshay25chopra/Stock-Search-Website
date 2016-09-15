<html>
<head>
</head>

<body>    
    <script>
        function clear1()
        {
            location.href="HW6.php";   
        }
    </script>
<style>
    th
    {
     text-align: left;
       border:1px;
        border-style: solid ;
         border: 1px solid lightgrey;
        border-collapse:collapse; 
        background-color: #E8E8E8;
    }
    
    td
    {
     text-align:center; 
       border:1px;
        border-style: solid ;
       border: 1px solid lightgrey;
        border-collapse:collapse; 
        background-color: #F8F8F8;
    }
    
           
    div td
    {
     text-align:left;   
    }
    
    table
    {
       
        border-collapse:collapse; 
        margin-left:auto;
        margin-right:auto;
    }
    
    .main{
     background-color:#E8E8E8;
        width: 400px;
        margin:auto;
    }
    
    #ss
    {
     font-size:26px;
        font-style:italic;
        font-weight:bold;
    }
    
    hr{
     color:lightgrey;  
        margin: 0 5 0 5;
    }
</style>    
 
<div class="main">    
 <form name="Form1" method="post" action="HW6.php" style="text-align:center">

     <span id="ss">Stock Search</span>
     <hr>
     <br>
     
     Company Name or Symbol :
     <input type ="text" name = "text" value="<?php if(isset($_POST['search'])) echo $_POST['text']; else if(isset($_GET['akshay'])) echo $_GET['akshay'];?>"required>
     <br>
     <input type ="submit" name="search" value="Search" style="margin-left:165px;margin-top:5px">
     <input type="reset" name="clear" value="Clear" onclick="clear1()">
        <br>
     <a href="http://www.markit.com/product/markit-on-demand">Powered by Markit on Demand</a>
     <br><br>
   </form>
</div>
      
<div>    
<?php if(isset($_POST['search']))
    {
    $temp = $_POST['text'];
    $url1 = "http://dev.markitondemand.com/MODApis/Api/v2/Lookup/xml?input=".$temp;
    $f = simplexml_load_file($url1);
    //echo $f->count();
    if($f->count() == 0)
        echo "<div align='center' style='background-color:#E8E8E8; width:500px; margin:auto ;border:1px solid lightgrey'>No Records has been found</div>";
    else
    {
        echo "<table>";
        echo "<tr><th>Name </th> <th> Symbol </th> <th> Exchange </th> <th> Details </th>";
        foreach ($f->LookupResult as $f2)
        {   
            echo "<tr><td>".$f2->Name."</td>";
            echo "<td>".$f2->Symbol."</td>";
            echo "<td>".$f2->Exchange."</td>";
            echo "<td><a href='HW6.php?akshay=$f2->Symbol'>More Info</a></td></tr>";
        }
        echo "</table>";
    }
    }
?>
</div>    
    
    
    
    <?php if(isset($_GET['akshay']))
{
    $temp2 = $_GET['akshay'];
    $url2 = "http://dev.markitondemand.com/MODApis/Api/v2/Quote/json?symbol=".$temp2;
    $a1 = file_get_contents($url2);
    $json = json_decode($a1,true);
    
    if($json['Status'] == "SUCCESS")
    {
    echo "<table width=600px>";
    
    echo "<tr><th>Name</th><td>".$json['Name']."</td></tr>"; 
    
    echo "<tr><th>Symbol</th><td>".$json['Symbol']."</td></tr>"; 
    
    echo "<tr><th>Last Price</th><td>".$json['LastPrice']."</td></tr>"; 
    if($json['Change']>0)
          echo "<tr><th>Change </th><td>".round($json['Change'],2)." <img src='http://cs-server.usc.edu:45678/hw/hw6/images/Green_Arrow_Up.png' height=12px width=12px> </td></tr>";
    else if($json['Change']<0)
          echo "<tr><th>Change </th><td>".round($json['Change'],2)." <img src='http://cs-server.usc.edu:45678/hw/hw6/images/Red_Arrow_Down.png' height=12px width=12px> </td></tr>";
    else
    echo "<tr><th>Change </th><td>".round($json['Change'],2)." </td></tr>"; 
    
    if($json['ChangePercent']<0)
    echo "<tr><th>Change Percent</th><td>".round($json['ChangePercent'],2)."% <img src='http://cs-server.usc.edu:45678/hw/hw6/images/Red_Arrow_Down.png' height=12px width=12px></td></tr>"; 
    else if($json['ChangePercent']>0)
    echo "<tr><th>Change Percent</th><td>".round($json['ChangePercent'],2)."% <img src='http://cs-server.usc.edu:45678/hw/hw6/images/Green_Arrow_Up.png' height=12px width=12px></td></tr>"; 
    else
    echo "<tr><th>Change Percent</th><td>".round($json['ChangePercent'],2)."%</td></tr>"; 
        
    date_default_timezone_set('America/Los_Angeles');    
    echo "<tr><th>Timestamp</th><td>".date("Y-m-d h:i A",strtotime($json['Timestamp']))."</td></tr>"; 
    
    echo "<tr><th>Market Cap</th><td>".round(($json['MarketCap']/1000000000),2)." B</td></tr>"; 
    
    echo "<tr><th>Volume</th><td>".number_format($json['Volume'])."</td></tr>"; 
    
    if(($json['LastPrice']-$json['ChangeYTD'])>0)
    echo "<tr><th>Change YTD</th><td>".round(($json['LastPrice']-$json['ChangeYTD']),2)."<img src='http://cs-server.usc.edu:45678/hw/hw6/images/Green_Arrow_Up.png' height=12px width=12px></td></tr>"; 
    
    else if(($json['LastPrice']-$json['ChangeYTD'])<0)
        echo "<tr><th>Change YTD</th><td>(".round(($json['LastPrice']-$json['ChangeYTD']),2).")<img src='http://cs-server.usc.edu:45678/hw/hw6/images/Red_Arrow_Down.png' height=12px width=12px> </td></tr>"; 
        
    else
        echo "<tr><th>Change YTD</th><td>".round(($json['LastPrice']-$json['ChangeYTD']),2)."</td></tr>"; 
        
    if($json['ChangePercentYTD']>0)                                    
    echo "<tr><th>Change Percent YTD</th><td>".round($json['ChangePercentYTD'],2)." <img src='http://cs-server.usc.edu:45678/hw/hw6/images/Green_Arrow_Up.png' height=12px width=12px> </td></tr>"; 
        
    else if($json['ChangePercentYTD']<0)
     echo "<tr><th>Change Percent YTD</th><td>".round($json['ChangePercentYTD'],2)." <img src='http://cs-server.usc.edu:45678/hw/hw6/images/Red_Arrow_Down.png' height=12px width=12px> </td></tr>"; 
    else
     echo "<tr><th>Change Percent YTD</th><td>".round($json['ChangePercentYTD'],2)." </td></tr>";
        
        
                                        
    echo "<tr><th>High</th><td>".$json['High']."</td></tr>"; 
                                        
    echo "<tr><th>Low</th><td>".$json['Low']."</td></tr>"; 
                                        
    echo "<tr><th>Open</th><td>".$json['Open']."</td></tr>"; 
    
    echo "</table>";
    
}
    else
        echo "<div align='center' style='background-color:#E8E8E8; width:500px; margin:auto ;border:1px solid lightgrey'>There is no stock information available</div>";
}
?>
    
<noscript></noscript>
</body>    

</html>
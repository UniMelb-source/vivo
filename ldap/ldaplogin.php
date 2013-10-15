<?php
function checkAuthentication($username, $password) {
  if ($connection = ldap_connect('ad1.unimelb.edu.au')) {
    ldap_set_option($connection, LDAP_OPT_PROTOCOL_VERSION, 3);
    ldap_set_option($connection, LDAP_OPT_REFERRALS, 0);
    $base = 'OU=People,DC=unimelb,DC=edu,DC=au';
    $distinguishedName = "CN=$username,$base";
    $return = false;
    try {
      if ($binding = ldap_bind($connection, $distinguishedName, $password)) {
        $search = ldap_search($connection, $base, "(CN=$username)", array('sAMAccountName', 'givenName', 'sn', 'mail'));
        if($entry = ldap_first_entry($connection, $search)) {
          $accountNames = ldap_get_values($connection, $entry, 'sAMAccountName');
          if($accountNames['count'] > 0) {
            $accountName = $accountNames[0];
          }
          $givenNames = ldap_get_values($connection, $entry, 'givenName');
          if($givenNames['count'] > 0) {
            $givenName = $givenNames[0];
          }
          $sns = ldap_get_values($connection, $entry, 'sn');
          if($sns['count'] > 0) {
            $sn = $sns[0];
          }
          $emails = ldap_get_values($connection, $entry, 'mail');
          if($emails['count'] > 0) {
            $email = $emails[0];
          }
          $return = array($accountName, $givenName, $sn, $email);
        }
        ldap_close($connection);
      }
    } catch (\ErrorException $e) {
      ldap_close($connection);
    }
  }
  return $return;
}

function curlSubmit($target, $token, $firstName, $lastName, $email) {
  $curl = curl_init();
  curl_setopt_array($curl, array(
    CURLOPT_RETURNTRANSFER => true,
    CURLOPT_URL => $target,
    CURLOPT_USERAGENT => 'VIVO CRDR Login',
    CURLOPT_POST => true,
    #TODO remove when SSL certificate is production ready
    CURLOPT_SSL_VERIFYPEER => false,
    CURLOPT_HTTPHEADER => array(
      'Content-Length: 0',
      "X-VIVO-Token: $token",
      "X-VIVO-First-Name: $firstName",
      "X-VIVO-Last-Name: $lastName",
      "X-VIVO-Email: $email"
    ),  
    CURLOPT_HEADER => true
  )); 
  $response = curl_exec($curl);
  $header_size = curl_getinfo($curl, CURLINFO_HEADER_SIZE);
  $header_string = substr($response, 0, $header_size);
  $headers = preg_split("/((\r(?!\n))|((?<!\r)\n)|(\r\n))/", $header_string);
  foreach($headers as $header):
    header($header);
  endforeach;
  $body = substr($response, $header_size);
  curl_close($curl);
  return $body;
}

if ($_SERVER['REQUEST_METHOD'] === 'POST'):
  $errors = array();
  if (empty($_POST['username'])):
    $errors[] = 'Username required';
  endif;
  if (empty($_POST['password'])):
    $errors[] = 'Password required';
  endif;
  if (empty($errors)):
    if (list($token, $firstName, $lastName, $email) = checkAuthentication($_POST['username'], $_POST['password'])):
      echo curlSubmit($_GET['target'], $token, $firstName, $lastName, $email);
    else:
      $errors[] = 'Invalid login';
    endif;
  endif;
endif;
?>
<html>
    <head>
        <meta charset="utf-8">
        <!-- Google Chrome Frame open source plug-in brings Google Chrome's open web technologies and speedy JavaScript engine to Internet Explorer-->
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

        <title>Login to VIVO CRDR</title>

        <link rel="stylesheet" href="//brand.unimelb.edu.au/web-templates/1-1/css/complete.css">
        <script type="text/javascript" async="" src="https://ssl.google-analytics.com/ga.js"></script><script type="text/javascript" src="//brand.unimelb.edu.au/global-header/js/injection.js"></script>

        <!-- vitro base styles (application-wide) -->
        <link rel="stylesheet" href="/css/vitro.css">
        <link rel="stylesheet" href="/css/login.css">
        <link rel="stylesheet" href="/css/edit.css"><link rel="stylesheet" href="/themes/unimelb/css/screen.css">
        <link rel="stylesheet" href="/js/jquery-ui/css/smoothness/jquery-ui.custom.css">

        <script type="text/javascript" src="/js/jquery.js"></script>
        <script type="text/javascript" src="/js/vitroUtils.js"></script>

        <!--[if lt IE 9]>
        <script type="text/javascript" src="/js/html5.js"></script>
        <![endif]-->

        <script type="text/javascript" src="/js/login/loginUtils.js"></script> 

        <!--[if lt IE 7]>
        <script type="text/javascript" src="/js/jquery_plugins/supersleight.js"></script>
        <![endif]-->

        <!--[if lt IE 7]>
        <link rel="stylesheet" href="/css/vitroIE6.css" />
        <![endif]-->

        <!--[if IE 7]>
        <link rel="stylesheet" href="/css/vitroIE7.css" />
        <![endif]-->

        <script type="text/javascript" src="/js/jquery-ui/js/jquery-ui.custom.min.js"></script>
        <script type="text/javascript" src="/themes/unimelb/js/jquery.cookie.js"></script>
        <script type="text/javascript" src="/themes/unimelb/js/rdr.js"></script>
        <script type="text/javascript" src="/themes/unimelb/js/tabs.js"></script><link rel="stylesheet" type="text/css" href="/themes/unimelb/css/tabs.css">
        <!--script type="text/javascript" src="/themes/unimelb/js/custom-form.js"></script-->
        <script type="text/javascript" src="/edit/forms/js/custom-form.js"></script>

        <!--[if lt IE 7]>
        <link rel="stylesheet" href="/themes/unimelb/css/ie6.css" />
        <![endif]-->

        <!--[if IE 7]>
        <link rel="stylesheet" href="/themes/unimelb/css/ie7.css" />
        <![endif]-->

        <!--[if (gte IE 6)&(lte IE 8)]>
        <script type="text/javascript" src="/js/selectivizr.js"></script>
        <![endif]-->

        <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico">

        <!-- Bootstrap Includes -->
        <link rel="stylesheet" href="/themes/unimelb/lib/bootstrap/css/bootstrap.min.css">
        <script type="text/javascript" src="/themes/unimelb/lib/bootstrap/js/bootstrap.min.js"></script>

        <!-- RDR Stylesheets -->
        <link rel="stylesheet" href="/themes/unimelb/css/rdr.css">
        <link rel="stylesheet" type="text/css" href="ldaplogin.css">
        <link href="//brand.unimelb.edu.au/global-header/css/style.css" rel="stylesheet" type="text/css">
    </head>
  <body class="no-logo science">
    <div id="g-header" role="banner">       <div class="wrapper">         <ul class="skiplinks">           <li><a href="#g-global-menu">Skip to navigation</a></li>           <li><a href="#main-content">Skip to content</a></li>         </ul>         <ol id="g-breadcrumb-menu">           <li id="g-breadcrumb-home"><a href="//www.unimelb.edu.au/">University Home</a></li>         </ol>         <div id="g-global-search">           <a href="http://search.unimelb.edu.au/" id="g-search-button" role="button" aria-haspopup="true">Search</a>           <ul id="g-audience-links">             <li><a href="http://campaign.unimelb.edu.au" class="mobile-hide">Support the Campaign</a></li>             <li><a href="https://my.unimelb.edu.au/">Current Students</a></li>             <li><a href="http://www.unimelb.edu.au/staff/">Staff</a></li>             <li><a href="http://alumni.unimelb.edu.au/" class="mobile-hide">Alumni</a></li>             <li><a href="http://library.unimelb.edu.au/">Library</a></li>             <li><a href="http://www.unimelb.edu.au/contact/">Contact &amp; Maps</a></li>           </ul>           <div id="g-global-search-overlay">             <form method="get" action="http://search.unimelb.edu.au/" name="g-searchform" id="g-searchform" role="search">               <label for="q"><span>Search the university</span></label>               <input accesskey="s" type="search" name="q" id="q" value="" title="Search the University">               <input type="submit" name="sa" id="g-global-search-submit" value="Search">             </form>           </div>         </div>         <hr>         <ol id="g-global-menu" role="navigation" aria-labelledby="g-global-menu-label">           <a id="g-global-menu-logo" href="http://www.unimelb.edu.au">The University of Melbourne</a>           <li id="g-global-menu-label">Browse the University</li>           <li><a href="//coursesearch.unimelb.edu.au" id="g-global-menu-study">Study</a></li>           <li><a href="http://unimelb.edu.au/research/" id="g-global-menu-research">Research</a></li>           <li><a href="http://unimelb.edu.au/engage/" id="g-global-menu-community">Engage</a></li>           <li><a href="http://about.unimelb.edu.au/" id="g-global-menu-about-us" class="last">About Us</a></li>         </ol>         <hr class="g-clear-floats">       </div>     </div>
    <h1>University of Melbourne user account</h1>
    <?php if(!empty($errors)): ?>
      <ul class=error>
      <?php foreach($errors as $error): ?>
        <li><?php print($error); ?></li>
      <?php endforeach; ?>
      </ul>
    <?php endif; ?>
    <form method="post" class="login-form load">
      <div class="login-input-wrap">
        <label for="username" class="pull-left"></label>
        <input type="text" name="username" <?php echo empty($_POST['username']) ? '' : 'value=' . $_POST['username'] . ' ' ?>placeholder="Username">
      </div>
      <span>Enter your staff username or your UMID</span>
      <div class="login-input-wrap">
        <label for="password" class="pull-left"></label>
        <input type="password" name="password" placeholder="Password">
      </div>
      <span>Enter your password</span>
      <input name="login" value="Login" type="submit" class="login-button">
      <?php if(!empty($errors)): ?>
        <a href="https://idm.unimelb.edu.au/idm/user/login.jsp">Forgot username or password?</a>
      <?php endif; ?>
    </form>
  </body>
</html>

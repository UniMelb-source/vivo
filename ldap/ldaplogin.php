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
        $search = ldap_search($connection, $base, "(CN=$username)", array('sAMAccountName'));
        if($entry = ldap_first_entry($connection, $search)) {
          $accountNames = ldap_get_values($connection, $entry, 'sAMAccountName');
          if($accountNames['count'] > 0) {
            $return = $accountNames[0];
          }
        }
        ldap_close($connection);
      }
    } catch (\ErrorException $e) {
      ldap_close($connection);
    }
  }
  return $return;
}

function curlSubmit($target, $token) {
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
      "X-VIVO-Token: $token"
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
    if ($token = checkAuthentication($_POST['username'], $_POST['password'])):
      echo curlSubmit($_GET['target'], $token);
    else:
      $errors[] = 'Invalid login';
    endif;
  endif;
endif;
?>
<html>
  <head>
    <title>Login to VIVO CRDR</title>
    <link rel="stylesheet" type="text/css" href="ldaplogin.css">
  </head>
  <body>
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

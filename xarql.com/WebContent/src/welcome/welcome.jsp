<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <!-- Global site tag (gtag.js) - Google Analytics -->
  <script async src="https://www.googletagmanager.com/gtag/js?id=${google_analytics_id}"></script>
  <script>
    window.dataLayer = window.dataLayer || [];
    function gtag(){dataLayer.push(arguments);}
    gtag('js', new Date());
    gtag('config', '${google_analytics_id}');
  </script>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>xarql</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta id="domain" value="${domain}">
  <link href="https://fonts.googleapis.com/css?family=Josefin+Sans:300,400,700" rel="stylesheet">
  <link rel="stylesheet" type="text/css" href="${domain}/src/common/${theme}-common.min.css">
  <link rel="shortcut icon" href="${domain}/logo.png" type="image/x-icon">
  <style id="font-size">
    html, body {
      font-size: ${font_size}
    }
  </style>
  <style id="font-weight">
    p {
      font-weight: ${font_weight}
    }
    h6, .bold {
      font-weight: ${font_weight + 200}
    }
  </style>
</head>
<body>

  <div id="welcome-wrapper">

      <div class="welcome-top">
        
        <div class="welcome-top-left">
          <svg class="welcome-logo" width="636" height="220" viewBox="0 0 636 220" fill="none" xmlns="http://www.w3.org/2000/svg">
            <g filter="url(#filter0_d)">
            <path d="M41.6797 68H86.6797L97.1797 96.5L107.68 68H152.68L124.18 120.5L154.18 173H109.18L97.1797 141.5L85.1797 173H40.1797L70.1797 120.5L41.6797 68ZM235.965 68C235.965 68.1 236.415 69.7 237.315 72.8C238.215 75.8 239.365 79.8 240.765 84.8C242.265 89.7 243.915 95.3 245.715 101.6C247.615 107.9 249.515 114.3 251.415 120.8C255.915 136.2 261.015 153.6 266.715 173H223.215L220.215 158H200.715L197.715 173H154.215L184.965 68H235.965ZM215.715 134L210.465 107L205.215 134H215.715ZM335.645 173L317.645 138.5H316.145V173H277.145V68C281.245 67.8 285.695 67.55 290.495 67.25C294.595 67.05 299.195 66.9 304.295 66.8C309.495 66.6 314.945 66.5 320.645 66.5C328.845 66.5 335.945 67.4 341.945 69.2C348.045 71 353.045 73.5 356.945 76.7C360.945 79.9 363.895 83.8 365.795 88.4C367.695 92.9 368.645 97.85 368.645 103.25C368.645 108.75 367.945 113.35 366.545 117.05C365.145 120.75 363.595 123.75 361.895 126.05C359.895 128.75 357.645 130.9 355.145 132.5L380.645 173H335.645ZM320.645 111.5C323.245 111.5 325.395 110.7 327.095 109.1C328.795 107.5 329.645 105.55 329.645 103.25C329.645 100.95 328.795 99 327.095 97.4C325.395 95.8 323.245 95 320.645 95H316.145V111.5H320.645ZM500.715 120.5C500.715 124.9 500.265 128.95 499.365 132.65C498.565 136.35 497.465 139.75 496.065 142.85C494.765 145.95 493.215 148.75 491.415 151.25C489.615 153.65 487.815 155.8 486.015 157.7C481.715 162.2 476.865 165.8 471.465 168.5C472.265 170.2 473.315 171.7 474.615 173C475.715 174.1 477.165 175.1 478.965 176C480.865 177 483.115 177.5 485.715 177.5C488.015 177.5 490.065 177.25 491.865 176.75C492.865 176.55 493.815 176.3 494.715 176V203C493.515 203.3 492.215 203.55 490.815 203.75C489.615 203.95 488.165 204.1 486.465 204.2C484.865 204.4 483.115 204.5 481.215 204.5C477.315 204.5 473.665 204.05 470.265 203.15C466.865 202.35 463.715 201.25 460.815 199.85C457.915 198.45 455.265 196.85 452.865 195.05C450.565 193.25 448.465 191.4 446.565 189.5C442.165 185.1 438.465 180.1 435.465 174.5C428.165 174.1 421.365 172.5 415.065 169.7C408.765 166.8 403.265 162.95 398.565 158.15C393.965 153.35 390.315 147.75 387.615 141.35C385.015 134.95 383.715 128 383.715 120.5C383.715 112.8 385.115 105.65 387.915 99.05C390.815 92.35 394.815 86.55 399.915 81.65C405.115 76.65 411.315 72.75 418.515 69.95C425.715 67.15 433.615 65.75 442.215 65.75C450.815 65.75 458.715 67.15 465.915 69.95C473.115 72.75 479.265 76.65 484.365 81.65C489.565 86.55 493.565 92.35 496.365 99.05C499.265 105.65 500.715 112.8 500.715 120.5ZM458.715 120.5C458.715 117.9 458.265 115.5 457.365 113.3C456.465 111.1 455.265 109.2 453.765 107.6C452.265 106 450.515 104.75 448.515 103.85C446.515 102.95 444.415 102.5 442.215 102.5C439.915 102.5 437.765 102.95 435.765 103.85C433.765 104.75 432.015 106 430.515 107.6C429.015 109.2 427.815 111.1 426.915 113.3C426.115 115.5 425.715 117.9 425.715 120.5C425.715 123.1 426.115 125.5 426.915 127.7C427.815 129.9 429.015 131.8 430.515 133.4C432.015 135 433.765 136.25 435.765 137.15C437.765 138.05 439.915 138.5 442.215 138.5C444.415 138.5 446.515 138.05 448.515 137.15C450.515 136.25 452.265 135 453.765 133.4C455.265 131.8 456.465 129.9 457.365 127.7C458.265 125.5 458.715 123.1 458.715 120.5ZM554.768 138.5H595.268V173H515.768V68H554.768V138.5Z" fill="#4A4A4A"/>
            </g>
            <defs>
            <filter id="filter0_d" x="0.179688" y="0.75" width="635.088" height="218.75" filterUnits="userSpaceOnUse" color-interpolation-filters="sRGB">
            <feFlood flood-opacity="0" result="BackgroundImageFix"/>
            <feColorMatrix in="SourceAlpha" type="matrix" values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"/>
            <feOffset dy="-25"/>
            <feGaussianBlur stdDeviation="20"/>
            <feColorMatrix type="matrix" values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.8 0"/>
            <feBlend mode="normal" in2="BackgroundImageFix" result="effect1_dropShadow"/>
            <feBlend mode="normal" in="SourceGraphic" in2="effect1_dropShadow" result="shape"/>
            </filter>
            </defs>
          </svg>
        </div>

        <div class="welcome-top-right">
          <div class="navigation">
            <input type="checkbox" id="navi">
            <label for="navi">
              <span class="navigation-burger">&nbsp;</span>
            </label>
            <ul class="navigation-list">
              <li><a href="${domain}/polr" class="navigation-button">polr</a></li>
              <li><a href="${domain}/chat" class="navigation-button">chat</a></li>
              <li><a href="${domain}/jott" class="navigation-button">jott</a></li>
              <li><a href="${domain}/help" class="navigation-button">help</a></li>
            </ul>
            <div class="navigation-bg">&nbsp;</div>
          </div>
        </div>
      </div>
      
      <div class="welcome-bottom">

        <div class="welcome-stats">
            <div class="welcome-stats-box">
              <h4>Stats</h4>
              <table>
                <tr><td>Total Sessions</td><td>${total_sessions}</td></tr>
                <tr><td>Auth Sessions</td><td>${auth_sessions}</td></tr>
                <tr><td>Live Chats</td><td>${live_chats}</td></tr>
                <tr><td>Online For</td><td>${live_time}</td></tr>
              </table>
            </div>
        </div>

        <div class="welcome-update">
            <div class="welcome-update-text">
              <h6><span>Wondering</span> what's new?</h6>
              <p>Check out <a href="https://twitter.com/xarql" class="welcome-link">xarql's official 
                <svg>
                  <use xlink:href="sprite.svg#icon-twitter"></use>
                </svg>
              </a></p>
              <p>Upload images on <a href="http://xarql.net/-/upload" class="welcome-link">xarql.net</a></p>
            </div>
        </div>
      </div>  

  </div>
</body>
</html>

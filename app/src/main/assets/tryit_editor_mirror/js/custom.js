    function submitTryit(){
          if(closeTagChecking() != 0){
            window.alert("Close tag is missing");
          }else{

            //window.alert(closeTagChecking());
            var text = editor.getValue();
            var ifr = document.createElement("iframe");
            ifr.setAttribute("frameborder", "0");
            ifr.setAttribute("id", "iframeOutput");  
            document.getElementById("iframewrapper").innerHTML = "";
            document.getElementById("iframewrapper").appendChild(ifr);
            var ifrw = (ifr.contentWindow) ? ifr.contentWindow : (ifr.contentDocument.document) ? ifr.contentDocument.document : ifr.contentDocument;
            ifrw.document.open();
            ifrw.document.write(text);
            ifrw.document.close();    
          }     
    };

      $( document ).ready(function() {
        var lastPosition = null;
        resizeWindow();
        $( window ).resize(function() {
          resizeWindow()
      });

      function calculatepercent(position) {
        var a = position;
        var b = $("body").width();
        var c = $("body").width() - position;

        $('div.main-left').width((returnPerCalc(a,b) + .4) + '%');
        $('div.main-right').width((returnPerCalc(c,b) - .5) + '%');
      };

      function returnPerCalc(a,b){
        var c = a/b;
        var d = c*100;
        return d;
      };

      $( "#draggable" ).draggable({ 
        axis: "x",
        start: function(a) {
          calculatepercent(a.target.offsetLeft);
        },
        drag: function(b) {
          calculatepercent(b.target.offsetLeft);
        },
        stop: function(c) {
          calculatepercent(c.target.offsetLeft);
          lastPosition = c.target.offsetLeft;
        }
      });

      function resizeWindow(){
        $("#mainContent").height($("body").height() - $(".header").height());
          $("#mainContentHolder,.left-inner-main,.right-inner-main,#draggable").height($("body").height() - ($(".header").height() + 10));

          // Convert the width from px to %
          var percent = $("div.main-left").width() / $("body").width() * 100;

          // Get the left postion of drag bar div incase window resized
          var position = (lastPosition != null)?((percent * $("body").width())/100):(($("body").width()/2));

          $("#draggable").css({
           'left' : position-5
        });
      };
    });
    
    function closeTagChecking(){
      code = editor.getValue();
      var close = 0;
      for(var i = 0; i < code.length; i++){
        if (code[i] == "<" && code[i+1] != "/" && code[i+1] != "!" && code[i+1] != "m"){
          close = close + 1;
        } else if (code[i] == "<" && code[i+1] == "/"){
          close = close - 1;
        }
      }
      return close;
    }

  var htmlDontClose = ["area", "base", "br", "col", "command", "embed", "hr", "img", "input", "keygen", "link", "meta", "param",
                       "source", "track", "wbr"];
  var htmlIndent = ["applet", "blockquote", "body", "button", "div", "dl", "fieldset", "form", "frameset", "h1", "h2", "h3", "h4",
                    "h5", "h6", "head", "html", "iframe", "layer", "legend", "object", "ol", "p", "select", "table", "ul"];
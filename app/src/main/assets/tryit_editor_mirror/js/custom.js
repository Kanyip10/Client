    function submitTryit(){
          //checking = closeTagChecking2().length;

          if(closeTagChecking2().length != 0){
            var unClosedTagBuffer = closeTagChecking2();
            for (var i = 0; i < unClosedTagBuffer.length; i++){
              unClosedTagBuffer[i] = "<" + unClosedTagBuffer[i] + ">";
            }
           window.alert("The close tag of " + unClosedTagBuffer + " is missing");
          //}else if(closeTagChecking2().length < -1){
            //window.alert("There are " + closeTagChecking2() + " unmatched close tag");
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
    };

    function closeTagChecking2(){
      code = editor.getValue();
      var htmlDontClose = ["area", "base", "br", "col", "command", "embed", "hr", "img", "input", "keygen", "link", "meta", "param",
                         "source", "track", "wbr"];
      var htmlIndent = ["applet", "blockquote", "body", "button", "canvas", "div", "dl", "fieldset", "form", "frameset", "h1", "h2", "h3", "h4",
                        "h5", "h6", "head", "html", "iframe", "layer", "legend", "object", "ol", "p", "script", "select", "style",
                        "table", "title", "ul"];
      var close = 0;
      var buffer = [];
      var index = 0;
      var unClosedTag = [];
      var unClosedIndex = 0;
      var closedTagIndex = 0;
      var tag = "";

      for(var i = 0; i < code.length; i++){
        if (code[i] == "<" && code[i+1] != "/" && code[i+1] != "!"){
          var x = i;
          while(code[x+1] != ">" && code[x+1] != " "){
            buffer[index] = code[x+1];
            x = x + 1;
            index = index + 1;
          }
          tag = buffer.join("");
          if (htmlIndent.includes(tag)){
            unClosedTag.splice(unClosedIndex, 0, tag);
            unClosedIndex = unClosedIndex + 1;
            close = close + 1;
          }
          tag = "";
          buffer = [];
          index = 0;
        } else if (code[i] == "<" && code[i+1] == "/"){
          var y = i + 1;
          while(code[y+1] != ">" && code[y+1] != " "){
            buffer[index] = code[y+1];
            y = y + 1;
            index = index + 1;         
          }
          tag = buffer.join("");  
          if (unClosedTag.includes(tag)){
            closedIndex = unClosedTag.indexOf(tag);
            unClosedTag.splice(closedIndex, 1);
            close = close - 1;
          }
          tag = "";
          buffer = [];
          index = 0;
        }
    }
    return unClosedTag;
  };



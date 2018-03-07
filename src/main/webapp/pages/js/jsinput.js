$(function(){
  var search2 = $('#logisticNumber');
  var icon2   = $('#search2btn');
  
  // handling the focus event on input2
  $(search2).on('focus', function(){
    $(this).animate({
      width: '100%'
    }, 400, function(){
      // callback function
    });
    $(icon2).animate({
      right: '10px'
    }, 400, function(){
      // callback function
    });
  });
  
  // handling the blur event on input2
  $(search2).on('blur', function(){
    if(search2.val() == '') {
      $(search2).animate({
        width: '100%'
      }, 400, function(){ });
      
      $(icon2).animate({
        right: '10px'
      }, 400, function(){ });
    }
  });
  
  // handling both form submissions
  $('#searchform, #searchform2').submit(function(e) {
    e.preventDefault();
  });
});
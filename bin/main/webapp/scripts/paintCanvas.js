/**
 * 
 */

//To hold the data for each action on the screen
var undoHistory = [];
 
//Function to save the states in history
function saveActions() {
var imgData = document.getElementById("chat-image-area-canvas").toDataURL("image/png");
undoHistory.push(imgData);
$('#undo').removeAttr('disabled');
}
 
//Actural Undo Function
function undoDraw(){
if(undoHistory.length > 0){
var undoImg = new Image();
$(undoImg).load(function(){
var context = document.getElementById("chat-image-area-canvas").getContext("2d");
context.drawImage(undoImg, 0,0);
});
undoImg.src = undoHistory.pop();
if(undoHistory.length == 0)
$('#undo').attr('disabled','disabled');
}
}
 
//Set the canvas defaults
//Including a white background
function canvasInit(){
context = document.getElementById("chat-image-area-canvas").getContext("2d");
context.lineCap = "round";
//Fill it with white background
context.save();
context.fillStyle = '#fff';
context.fillRect(0, 0, context.canvas.width, context.canvas.height);
context.restore();
}
 
// create a function to pass touch events and coordinates to drawer
function draw(event){
// get the touch coordinates
var coors = {
x: event.targetTouches[0].pageX,
y: event.targetTouches[0].pageY
};
// pass the coordinates to the appropriate handler
drawer[event.type](coors);
}
 
$(function(){
var canvas, cntxt, top, left, draw, draw = 0;
//Get the canvas element
//var canvas = $("#chat-image-area-canvas");
canvas = document.getElementById("chat-image-area-canvas");
cntxt = canvas.getContext("2d");
top = $('#chat-image-area-canvas').offset().top;
left = $('#chat-image-area-canvas').offset().left;
canvasInit();
 
// attach the touchstart, touchmove, touchend event listeners.
canvas.addEventListener('touchstart',draw, false);
canvas.addEventListener('touchmove',draw, false);
canvas.addEventListener('touchend',draw, false);
 
var drawer = {
isDrawing: false,
touchstart: function(coors){
cntxt.beginPath();
cntxt.moveTo(coors.x, coors.y);
this.isDrawing = true;
},
touchmove: function(coors){
if (this.isDrawing) {
cntxt.lineTo(coors.x, coors.y);
cntxt.stroke();
}
},
touchend: function(coors){
if (this.isDrawing) {
this.touchmove(coors);
this.isDrawing = false;
}
}
};
 
// prevent elastic scrolling
document.body.addEventListener('touchmove',function(event){
event.preventDefault();
},false);
 
//Drawing Code
$('#chat-image-area-canvas').mousedown(function(e){
if(e.button == 0){
draw = 1;
//Start The drawing flow
//Save the state
saveActions();
cntxt.beginPath();
cntxt.moveTo(e.pageX-left, e.pageY-top);
}
else{
draw = 0;
}
})
.mouseup(function(e){
if(e.button != 0){
draw = 1;
}
else{
draw = 0;
cntxt.lineTo(e.pageX-left+1, e.pageY-top+1);
cntxt.stroke();
cntxt.closePath();
}
})
.mousemove(function(e){
if(draw == 1){
cntxt.lineTo(e.pageX-left+1, e.pageY-top+1);
cntxt.stroke();
}
});
 
//Extra Links Code
$('#export').click(function(e){
e.preventDefault();
window.open(canvas.toDataURL(), 'Canvas Export','height=400,width=400');
//console.log(canvas.toDataURL());
});
$('#clear').click(function(e){
e.preventDefault();
canvas.width = canvas.width;
canvas.height = canvas.height;
canvasInit();
$('#chat-image-area-colors li:first').click();
$('#brush_size').change();
undoHistory = [];
});
$('#brush_size').change(function(e){
cntxt.lineWidth = $(this).val();
});
$('#chat-image-area-colors li').click(function(e){
e.preventDefault();
$('#chat-image-area-colors li').removeClass('selected');
$(this).addClass('selected');
cntxt.strokeStyle = $(this).css('background-color');
});
 
//Undo Binding
$('#undo').click(function(e){
e.preventDefault();
undoDraw()
});
 
//Init the brush and color
$('#chat-image-area-colors li:first').click();
$('#brush_size').change();
 
})
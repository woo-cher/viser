/**
 * 
 */
(function() {

  /* 마우스 오른쪽 메뉴 변수 */
  var test = document.getElementById("context-menus");

  /* 마우스 클릭 리스너를 초기 실행시킨다. */
  function init() {
    rightMouseListener();
    leftMouseListener();
  }

  /* 마우스 왼클릭 감지 */
  function leftMouseListener() {
    document.addEventListener("click", function(e) {
      toggleOnOff(0);
    })
  }

  /* 마우스 우클릭 감지 */
  function rightMouseListener() {
    document.addEventListener("contextmenu", function(e) {
      event.preventDefault();
      toggleOnOff(1);
      showMenu(e.x, e.y);
    });
  }

  /* 마우스 메뉴 on & off */
  function toggleOnOff(num) {
    num === 1 ? test.classList.add("active") : test.classList.remove("active");
  }

  /* 마우스 클릭한 지점에서 메뉴 보여줌 */
  function showMenu(x, y) {
    console.log(test);
    test.style.top = y + "px";
    test.style.left = x + "px";

  }

  init();
})();

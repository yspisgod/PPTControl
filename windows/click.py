#! python3
import pyautogui, sys,websocket
import _thread as thread
import time
def onclick():
    x,y=pyautogui.size()
    pyautogui.moveTo(x/2, y/2)
    pyautogui.click()
def back():
    pyautogui.scroll(10)

def on_message(ws, message):
    # 向前翻页
    print (message)
    if(message == "ppt_move"):
        onclick()
    # 向后翻页
    if(message=="ppt_back"):
        back()

def on_error(ws, error):
    print(error)

def on_close(ws):
    print("### closed ###")

def on_open(ws):
    print("### open ###")
    # def run(*args):
    #     for i in range(30000):
    #         time.sleep(1)
    #         ws.send("Hello %d" % i)
    #     time.sleep(1)
    #     ws.close()
    #     print("thread terminating...")
    # thread.start_new_thread(run, ())


if __name__ == "__main__":
    websocket.enableTrace(True)
    ws = websocket.WebSocketApp("ws://139.199.71.131:8888",
                                on_message = on_message,
                                on_error = on_error,
                                on_close = on_close)
    ws.on_open = on_open

    ws.run_forever()

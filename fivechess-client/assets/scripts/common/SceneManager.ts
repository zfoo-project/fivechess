import {NetManager} from "./NetManager";

const {ccclass, property} = cc._decorator;


export default class SceneManager {
    public static loadScene(sceneName) {
        NetManager.setLockMsgQueue(true);
        cc.director.loadScene(sceneName, () => {
            NetManager.setLockMsgQueue(false);
        });
    }

}

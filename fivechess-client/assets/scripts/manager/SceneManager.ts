import NetManager from "./NetManager";

export default class SceneManager {
    /**
     * 切换场景
     * @param sceneName
     */
    public static loadScene(sceneName) {
        NetManager.inst().lockMsgQueue();

        cc.director.loadScene(sceneName, () => {
            NetManager.inst().unLockMsgQueue();
        })
    }
}

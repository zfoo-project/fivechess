import NetManager from "./NetManager";

export default class SceneManager {
    private static _inst: SceneManager = null;

    public static inst(): SceneManager {
        if (this._inst == null) {
            this._inst = new SceneManager();
        }
        return this._inst;
    }

    /**
     * 切换场景
     * @param sceneName
     */
    public loadScene(sceneName): void {
        NetManager.inst().setLock(true);
        cc.director.loadScene(sceneName, () => {
            NetManager.inst().setLock(false);
        })
    }
}

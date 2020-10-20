package com.tcashcroft.t65.model;

import lombok.Data;

@Data
public class Ship {

    private String id;
    private Utils.Faction faction;
    private String name;
    private ShipType shipType;
    private int nameLimit;
    private String callSign;
    private int frontArc;
    private int rearArc;
    private int turretArc;
    private int agility;
    private int hull;
    private int shield;
    private int force;
    private String abilityText;
    private Action action1;
    private Action action2;
    private Action action3;
    private Action action4;
    private int astromechUpgrades;
    private int cannonUpgrades;
    private int cargoUpgrades;
    private int configurationUpgrades;
    private int commandUpgrades;
    private int crewUpgrades;
    private int deviceUpgrades;
    private int forceUpgrades;
    private int gunnerUpgrades;
    private int hardpointUpgrades;
    private int hyperdriveUpgrades;
    private int illicitUpgrades;
    private int modificationUpgrades;
    private int missileUpgrades;
    private int sensorUpgrades;
    private int tacticalRelayUpgrades;
    private int talentUpgrades;
    private int teamUpgrades;
    private int techUpgrades;
    private int titleUpgrades;
    private int torpedoUpgrades;
    private int turretUpgrades;
    private int pointsCost;
    private boolean hyperspaceLegal;
    private boolean extendedLegal;
    private String dialCode;
    private Size size;
    private int initiative;

    public enum Size {SMALL("small"), MEDIUM("medium"), LARGE("large"), HUGE("huge");

        private String value;

        Size(String value) {this.value = value.toLowerCase();}

        public String getValue() {
            return value;
        }
    }

    public enum ShipType {
        TIE_BA_INTERCEPTOR("tie-ba-interceptor"),
        TIE_FO_FIGHTER("tie-fo-fighter"),
        TIE_SF_FIGHTER("tie-sf-fighter"),
        TIE_VN_SILENCER("tie-vn-silencer"),
        UPSILON_CLASS_COMMAND_SHUTTLE("upsilon-class-command-shuttle"),
        XI_CLASS_LIGHT_SHUTTLE("xi-class-light-shuttle"),
        ALPHA_CLASS_STAR_WING("alpha-class-star-wing"),
        LAMBDA_CLASS_T_4A_SHUTTLE("lambda-class-t-4a-shuttle"),
        TIE_ADVANCED_V1("tie-advanced-v1"),
        TIE_ADVANCED_X1("tie-advanced-x1"),
        TIE_AG_AGGRESSOR("tie-ag-aggressor"),
        TIE_CA_PUNISHER("tie-ca-punisher"),
        TIE_D_DEFENDER("tie-d-defender"),
        TIE_IN_INTERCEPTOR("tie-in-interceptor"),
        TIE_LN_FIGHTER("tie-ln-fighter"),
        TIE_PH_PHANTOM("tie-ph-phantom"),
        TIE_RB_HEAVY("tie-rb-heavy"),
        TIE_REAPER("tie-reaper"),
        TIE_SA_BOMBER("tie-sa-bomber"),
        TIE_SK_STRIKER("tie-sk-striker"),
        VT_49_DECIMATOR("vt-49-decimator"),
        ARC_170_STARFIGHTER("arc-170-starfighter"),
        BTL_B_Y_WING("btl-b-y-wing"),
        DELTA_7_AETHERSPRITE("delta-7-aethersprite"),
        ETA_2_ACTIS("eta-2-actis"),
        LAAT_I_GUNSHIP("latt-i-gunship"),
        NABOO_ROYAL_N_1_STARFIGHTER("naboo-royal-n-1-starfighter"),
        NIMBUS_CLASS_V_WING("numbus-class-v-wing"),
        V_19_TORRENT_STARFIGHTER("v-19-torrent-starfighter"),
        A_SF_01_B_WING("a-sf-01-b-wing"),
        ATTACK_SHUTTLE("attack-shuttle"),
        AUZITUCK_GUNSHIP("auzituck-gunship"),
        BTL_A4_Y_WING("btl-a4-y-wing"),
        BTL_S8_K_WING("btl-s8-k-wing"),
        E_WING("e-wing"),
        HWK_290_LIGHT_FREIGHTER("hwk-290-light-freighter"),
        MODIFIED_YT_1300_LIGHT_FREIGHTER("modified-yt-1300-light-freighter"),
        RZ_1_A_WING("rz-1-a-wing"),
        SHEATHIPEDE_CLASS_SHUTTLE("sheathipede-class-shuttle"),
        T_65_X_WING("t-65-x-wing"),
        UT_60D_U_WING("ut-60d-u-wing"),
        VCX_100_LIGHT_FREIGHTER("vcx-100-light-freighter"),
        YT_2400_LIGHT_FREIGHTER("yt-2400-light-freighter"),
        Z_95_AF4_HEADHUNTER("z-95-af4-headhunter"),
        FIREBALL("fireball"),
        MG_100_STARFORTRESS_SF_17("mg-100-starfortress-sf-17"),
        RESISTANCE_TRANSPORT_POD("resistance-transport-pod"),
        RESISTANCE_TRANSPORT("resistance-transport"),
        RZ_2_A_WING("rz-2-a-wing"),
        SCAVANGED_YT_1300("scavanged-yt-1300"),
        T_70_X_WING("t-70-x-wing"),
        AGGRESSOR_ASSAULT_FIGHTER("aggressor-assault-fighter"),
        CUSTOMIZED_YT_1300_LIGHT_FREIGHTER("customized-yt-1300-lighter-freighter"),
        ESCAPE_CRAFT("escape-craft"),
        FANG_FIGHTER("fang-fighter"),
        FIRESPRAY_CLASS_PATROL_CRAFT("firespray-class-patrol-craft"),
        G_1A_STARFIGHTER("g-1a-starfighter"),
        JUMPMASTER_5000("jumpmaster-5000"),
        KIHRAXZ_FIGHTER("kihraxz-fighter"),
        LANCER_CLASS_PURSUIT_CRAFT("lancer-class-pursuit-craft"),
        M12_L_KIMOGILA_FIGHTER("m12-l-kimogila-fighter"),
        M3_A_INTERCEPTOR("m3-a-interceptor"),
        MODIFIED_TIE_LN_FIGHTER("modified-tie-ln-fighter"),
        QUADRIJET_TRANSFER_SPACETUG("quadriject-transfer-spacetug"),
        SCURRG_H_6_BOMBER("scurrg-h-6-bomber"),
        STARVIPER_CLASS_ATTACK_PLATFORM("starviper-class-attack-platform"),
        YV_666_LIGHT_FREIGHTER("yv-666-light-freighter"),
        BELBULLAB_22_STARFIGHTER("belbullab-22-starfighter"),
        DROID_TRI_FIGHTER("droid-tri-fighter"),
        HMP_DROID_GUNSHIP("hmp-droid-gunship"),
        HYENA_CLASS_DROID_BOMBER("hyena-class-droid-bomber"),
        NANTEX_CLASS_STARFIGHTER("nantex-class-starfighter"),
        SITH_INFILTRATOR("sith-infiltrator"),
        VULTURE_CLASS_DROID_FIGHTER("vulture-class-droid-fighter");

        private String value;

        ShipType(String value) {
            this.value = value.toLowerCase();
        }

        public String getValue() {
            return value;
        }
    }

}

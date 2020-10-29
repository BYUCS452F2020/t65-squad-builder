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
    private String pilotAbilityText;
    private String shipAbilityText;
    private Action action1;
    private Action action2;
    private Action action3;
    private Action action4;
    private Action action5;
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

    public enum Size {SMALL("SMALL"), MEDIUM("MEDIUM"), LARGE("LARGE"), HUGE("HUGE");

        private String value;

        Size(String value) {this.value = value.toLowerCase();}

        public String getValue() {
            return value;
        }
    }

    public enum ShipType {
        TIE_BA_INTERCEPTOR("TIE_BA_INTERCEPTOR"),
        TIE_FO_FIGHTER("TIE_FO_FIGHTER"),
        TIE_SF_FIGHTER("TIE_SF_FIGHTER"),
        TIE_VN_SILENCER("TIE_VN_SILENCER"),
        UPSILON_CLASS_COMMAND_SHUTTLE("UPSILON_CLASS_COMMAND_SHUTTLE"),
        XI_CLASS_LIGHT_SHUTTLE("XI_CLASS_LIGHT_SHUTTLE"),
        ALPHA_CLASS_STAR_WING("ALPHA_CLASS_STAR_WING"),
        LAMBDA_CLASS_T_4A_SHUTTLE("LAMBDA_CLASS_T_4A_SHUTTLE"),
        TIE_ADVANCED_V1("TIE_ADVANCED_V1"),
        TIE_ADVANCED_X1("TIE_ADVANCED_X1"),
        TIE_AG_AGGRESSOR("TIE_AG_AGGRESSOR"),
        TIE_CA_PUNISHER("TIE_CA_PUNISHER"),
        TIE_D_DEFENDER("TIE_D_DEFENDER"),
        TIE_IN_INTERCEPTOR("TIE_IN_INTERCEPTOR"),
        TIE_LN_FIGHTER("TIE_LN_FIGHTER"),
        TIE_PH_PHANTOM("TIE_PH_PHANTOM"),
        TIE_RB_HEAVY("TIE_RB_HEAVY"),
        TIE_REAPER("TIE_REAPER"),
        TIE_SA_BOMBER("TIE_SA_BOMBER"),
        TIE_SK_STRIKER("TIE_SK_STRIKER"),
        VT_49_DECIMATOR("VT_49_DECIMATOR"),
        ARC_170_STARFIGHTER("ARC_170_STARFIGHTER"),
        BTL_B_Y_WING("BTL_B_Y_WING"),
        DELTA_7_AETHERSPRITE("DELTA_7_AETHERSPRITE"),
        ETA_2_ACTIS("ETA_2_ACTIS"),
        LAAT_I_GUNSHIP("LAAT_I_GUNSHIP"),
        NABOO_ROYAL_N_1_STARFIGHTER("NABOO_ROYAL_N_1_STARFIGHTER"),
        NIMBUS_CLASS_V_WING("NIMBUS_CLASS_V_WING"),
        V_19_TORRENT_STARFIGHTER("V_19_TORRENT_STARFIGHTER"),
        A_SF_01_B_WING("A_SF_01_B_WING"),
        ATTACK_SHUTTLE("ATTACK_SHUTTLE"),
        AUZITUCK_GUNSHIP("AUZITUCK_GUNSHIP"),
        BTL_A4_Y_WING("BTL_A4_Y_WING"),
        BTL_S8_K_WING("BTL_S8_K_WING"),
        E_WING("E_WING"),
        HWK_290_LIGHT_FREIGHTER("HWK_290_LIGHT_FREIGHTER"),
        MODIFIED_YT_1300_LIGHT_FREIGHTER("MODIFIED_YT_1300_LIGHT_FREIGHTER"),
        RZ_1_A_WING("RZ_1_A_WING"),
        SHEATHIPEDE_CLASS_SHUTTLE("SHEATHIPEDE_CLASS_SHUTTLE"),
        T_65_X_WING("T_65_X_WING"),
        UT_60D_U_WING("UT_60D_U_WING"),
        VCX_100_LIGHT_FREIGHTER("VCX_100_LIGHT_FREIGHTER"),
        YT_2400_LIGHT_FREIGHTER("YT_2400_LIGHT_FREIGHTER"),
        Z_95_AF4_HEADHUNTER("Z_95_AF4_HEADHUNTER"),
        FIREBALL("FIREBALL"),
        MG_100_STARFORTRESS_SF_17("MG_100_STARFORTRESS_SF_17"),
        RESISTANCE_TRANSPORT_POD("RESISTANCE_TRANSPORT_POD"),
        RESISTANCE_TRANSPORT("RESISTANCE_TRANSPORT"),
        RZ_2_A_WING("RZ_2_A_WING"),
        SCAVENGED_YT_1300("SCAVENGED_YT_1300"),
        T_70_X_WING("T_70_X_WING"),
        AGGRESSOR_ASSAULT_FIGHTER("AGGRESSOR_ASSAULT_FIGHTER"),
        CUSTOMIZED_YT_1300_LIGHT_FREIGHTER("CUSTOMIZED_YT_1300_LIGHT_FREIGHTER"),
        ESCAPE_CRAFT("ESCAPE_CRAFT"),
        FANG_FIGHTER("FANG_FIGHTER"),
        FIRESPRAY_CLASS_PATROL_CRAFT("FIRESPRAY_CLASS_PATROL_CRAFT"),
        G_1A_STARFIGHTER("G_1A_STARFIGHTER"),
        JUMPMASTER_5000("JUMPMASTER_5000"),
        KIHRAXZ_FIGHTER("KIHRAXZ_FIGHTER"),
        LANCER_CLASS_PURSUIT_CRAFT("LANCER_CLASS_PURSUIT_CRAFT"),
        M12_L_KIMOGILA_FIGHTER("M12_L_KIMOGILA_FIGHTER"),
        M3_A_INTERCEPTOR("M3_A_INTERCEPTOR"),
        MODIFIED_TIE_LN_FIGHTER("MODIFIED_TIE_LN_FIGHTER"),
        QUADRIJET_TRANSFER_SPACETUG("QUADRIJET_TRANSFER_SPACETUG"),
        SCURRG_H_6_BOMBER("SCURRG_H_6_BOMBER"),
        STARVIPER_CLASS_ATTACK_PLATFORM("STARVIPER_CLASS_ATTACK_PLATFORM"),
        YV_666_LIGHT_FREIGHTER("YV_666_LIGHT_FREIGHTER"),
        BELBULLAB_22_STARFIGHTER("BELBULLAB_22_STARFIGHTER"),
        DROID_TRI_FIGHTER("DROID_TRI_FIGHTER"),
        HMP_DROID_GUNSHIP("HMP_DROID_GUNSHIP"),
        HYENA_CLASS_DROID_BOMBER("HYENA_CLASS_DROID_BOMBER"),
        NANTEX_CLASS_STARFIGHTER("NANTEX_CLASS_STARFIGHTER"),
        SITH_INFILTRATOR("SITH_INFILTRATOR"),
        VULTURE_CLASS_DROID_FIGHTER("VULTURE_CLASS_DROID_FIGHTER");

        private String value;

        ShipType(String value) {
            this.value = value.toLowerCase();
        }

        public String getValue() {
            return value;
        }
    }

}

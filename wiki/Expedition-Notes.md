# Expedition Notes (progression & quests)

NeroRuins records what explorers do through **Neroland Core** progression signals, and ships a quest
chapter that reads them. Nothing on this page is completable yet: NeroRuins has no structures, so no
code opens the gates below or counts a claimed vault. The signals and the chapter are in place so that,
when ruins land, questing them costs only data.

## Personal milestones (player-scope record gates)

Each gate is a data file under `data/neroruins/neroland_gates/` with `"scope": "player"` and no
`requires`. They record that a player did something; they gate nothing and are never checked for
access (vault locks and puzzle doors keep their own logic).

| Gate | Opens when |
| --- | --- |
| `neroruins:first_log` | a player reads a data log for the first time |
| `neroruins:first_vault` | a player opens a locked vault |
| `neroruins:puzzle_solver` | a puzzle door opens for the player who completed it |
| `neroruins:temple_cleared` | the last guardian of a temple dies — for every player who damaged it |

## World milestone (threshold channel)

`neroruins:vault_claimed` fires on Core's threshold bus each time the number of claimed vaults of one
**structure type** reaches 1, 10 or 50 in a world. The crossing's scope is the structure type id (for
example `neroruins:lava_temple`) and its value is that type's new count. Counts are kept per world in
the `neroruins:vault_ledger` saved-data store.

The scope is never a player, a UUID or a position: a ruin's coordinates plus a time could locate a
player, so the channel says *how much* has been plundered, never *where* or *by whom*.

## Relics

The item tag `neroruins:relics` (`data/neroruins/tags/item/relics.json`) lists the relic items quests
and other mods can ask for. It is empty until relic items exist.

## The quest chapter

When **NeroQuests** is installed, the chapter `neroruins:expedition_notes` "Expedition Notes" appears in
the quest book. Without NeroQuests the files are inert; NeroRuins has no dependency on it.

| Quest | Scope | Completes when |
| --- | --- | --- |
| First Log | player | `neroruins:first_log` is open for you |
| Vault Cracked | player | `neroruins:first_vault` is open for you (after First Log) |
| Puzzle Solver | player | `neroruins:puzzle_solver` is open for you |
| Temple Cleared | player | `neroruins:temple_cleared` is open for you |
| Relic Hunter | player | you hold three items tagged `neroruins:relics` |
| First Claim | server | a `neroruins:vault_claimed` crossing with value ≥ 1 |
| Plundered Worlds | server | a `neroruins:vault_claimed` crossing with value ≥ 50 (after First Claim) |

Rewards are experience, a few plain items, and two **hook** rewards handled by NeroRuins itself:

| Hook gate | Granted by | Effect |
| --- | --- | --- |
| `neroruins:hook/unlock_log` | First Log | will unlock one lore entry in your data-log archive |
| `neroruins:hook/reveal_ruin` | Temple Cleared | will give you a one-use survey map to the nearest unclaimed ruin in your dimension |

Until the archive and survey maps exist, each hook sends you one chat line saying the reward has been
reserved, and re-arms. The real effects arrive with the content.

## Privacy

Personal milestones live in Neroland Core's player-scope gate store (covered by Core's own privacy
notes and erasure hook). The vault ledger holds structure type ids and counts only. No log line, chat
message or event carries a player name, UUID or position.

## See also

- [Home](Home.md)

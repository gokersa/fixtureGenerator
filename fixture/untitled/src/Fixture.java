import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Fixture {

    private Team leftTeam;
    private Team rightTeam;
    private static int rounds;
    private static int roundMatches;

    public Fixture(Team leftTeam, Team rightTeam) {
        this.leftTeam = leftTeam;
        this.rightTeam = rightTeam;
    }

    public Team getLeftTeam() {
        return leftTeam;
    }

    public void setLeftTeam(Team leftTeam) {
        this.leftTeam = leftTeam;
    }

    public Team getRightTeam() {
        return rightTeam;
    }

    public void setRightTeam(Team rightTeam) {
        this.rightTeam = rightTeam;
    }
    // Fikstürde 1'den fazla takım olursa kullanılır
    public static synchronized ArrayList<Fixture> FixtureGenerator(List<Team> teams) {
        if (teams.size() % 2 != 0) {
            teams.add(new Team("Bay"));
        }


        ArrayList<Fixture> fixtureArrayList = new ArrayList<>();
        ArrayList<Team> tempTeams = new ArrayList<>();
        int totalList = teams.size();
        rounds = (totalList - 1);
        roundMatches = totalList / 2;
        // takımları karıştırmaya yarar
        Collections.shuffle(teams);

        for(int i=0; i<rounds; i++) {
            for (int j=0; j<roundMatches; j++){
                int leftTeam = j;
                int rightTeam = ((teams.size()-1)-j);
                fixtureArrayList.add(new Fixture(teams.get(leftTeam), teams.get(rightTeam))); // takımları yan yana yazar
            }
            tempTeams.clear(); //geçici takım listesini siler. orijinal listemizdeki elemanları kaydırarak işleme devam ederiz
            tempTeams.add(teams.get(0)); // ilk takımı sabit tutup sondaki elemanı onu yanına ekleriz
            tempTeams.add(teams.get(teams.size()-1)); // sondaki takımı ekleme işlemi

            for (int k=1; k<teams.size()-1; k++) {
                tempTeams.add(teams.get(k));
            } // kalan takımları eklemek için bu for döngüsünü kullanıyoruz.
            for (int j=0; j<roundMatches; j++) {
                int leftTeam = j;
                int rightTeam = ((teams.size()-1)-j);
                fixtureArrayList.add(new Fixture(tempTeams.get(rightTeam), tempTeams.get(leftTeam)));
            } // bu for döngüsü 2. devre maçları için yazıldı. dikkat edersek right team bu sefer sola yazıldı.

            teams.clear();
            teams.addAll(tempTeams); // tempTeams'te oluşturduğumuz liste orijinal listemize ekleniyor
        }
        return fixtureArrayList;
    }

    public static void print (List<Fixture> fixtureList) {
        int round = 1;

        for (int i=0; i<fixtureList.size(); i++) {
            if(i%roundMatches == 0) {
                System.out.println("* Round : " + round++);
            }
            System.out.println(fixtureList.get(i).getLeftTeam().getTeamName() + " vs. " + fixtureList.get(i).getRightTeam().getTeamName());
        }
    }
}


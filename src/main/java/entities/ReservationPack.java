    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package entities;

    import java.util.Date;

    public class ReservationPack {
        private int id;
        private Date dateReservationPack;
        private int nbrParticipantPack;
        private Pack pack;
        private User user;
        private int likes;
        private int dislikes;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Date getDateReservationPack() {
            return dateReservationPack;
        }

        public void setDateReservationPack(Date dateReservationPack) {
            this.dateReservationPack = dateReservationPack;
        }

        public int getNbrParticipantPack() {
            return nbrParticipantPack;
        }

        public void setNbrParticipantPack(int nbrParticipantPack) {
            this.nbrParticipantPack = nbrParticipantPack;
        }

        public Pack getPack() {
            return pack;
        }

        public void setPack(Pack pack) {
            this.pack = pack;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public int getDislikes() {
            return dislikes;
        }

        public void setDislikes(int dislikes) {
            this.dislikes = dislikes;
        }

        public void incrementDislikes() {
            this.dislikes++;
        }

        public void incrementLikes() {
            this.likes++;
        }

        public boolean checkAndDeleteIfRequired() {
            return this.dislikes - this.likes >= 2;
        }

        @Override
        public String toString() {
            return "ReservationPack{" +
                    " dateReservationPack=" + dateReservationPack +
                    ", nbrParticipantPack=" + nbrParticipantPack +
                    ", pack=" + pack +
                    ", user=" + user +
                    ", likes=" + likes +
                    ", dislikes=" + dislikes +
                    '}';
        }
    }

